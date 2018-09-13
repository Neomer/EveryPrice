using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class User : BaseEntity, IUser
    {
        public virtual string Username { get; set; }
    }
}
